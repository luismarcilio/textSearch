package com.luismarcilio.grocery_brasil_app.textSearch.repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.luismarcilio.grocery_brasil_app.textSearch.WithDebug;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.DocumentFromProduct;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.Product;
import com.luismarcilio.grocery_brasil_app.textSearch.domain.ProductFromDocument;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductRepository implements CrudRepository<Product,String>{
    private final IndexWriter indexWriter;
    private final SearcherManager searcherManager;
    private final Analyzer analyzer;
    
    public ProductRepository(IndexWriter indexWriter, SearcherManager searcherManager, Analyzer analyzer) {
        this.indexWriter = indexWriter;
        this.analyzer = analyzer;
        this.searcherManager = searcherManager;
    }
    
    @Override
	@WithDebug
    public void save(Product entity) {
        DocumentFromProduct documentFromProduct = new DocumentFromProduct();
        try {
            this.indexWriter.updateDocument(new Term("id", entity.getId()), documentFromProduct.getDocument(entity));
            this.indexWriter.commit();
        } catch (IOException e) {
            throw new ProductRepositoryRuntimeException(e);
        }
    }

    @Override
	@WithDebug
    public void saveAll(List<Product> entityList) {
        for(Product entity : entityList){
            this.save(entity);
        }
        
    }

	@WithDebug
    private Product findByDocId(Integer id) {
        Document doc;
        IndexSearcher indexSearcher;
        try {
            searcherManager.maybeRefresh();
            indexSearcher = searcherManager.acquire();
        } catch (IOException e1) {
            throw new ProductRepositoryRuntimeException(e1);
        }
        try {
            doc = indexSearcher.doc(id);
            ProductFromDocument productFromDocument = new ProductFromDocument();
            return productFromDocument.getEntity(doc);
        } catch (IOException e) {
            throw new ProductRepositoryRuntimeException(e);
        }       
        finally{
            try {
                searcherManager.release(indexSearcher);
            } catch (IOException e) {
                log.error(e.toString());
            }
        } 
    }

	@WithDebug
    public Stream<Product> findAll(){
        Query query = new MatchAllDocsQuery();
        IndexSearcher indexSearcher;
        try {
            searcherManager.maybeRefresh();
            indexSearcher = searcherManager.acquire();
        } catch (IOException e1) {
            throw new ProductRepositoryRuntimeException(e1);

        }
        try {
            int numberOfDocs = indexSearcher.count(query);
            TopDocs docs = indexSearcher.search(query, numberOfDocs);
            return Arrays.stream(docs.scoreDocs).map(scoreDoc -> findByDocId(scoreDoc.doc));
        } catch (IOException e) {
            throw new ProductRepositoryRuntimeException(e);
        } catch(IllegalArgumentException e){
            return Stream.empty();
        }
        finally{
            try {
                searcherManager.release(indexSearcher);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

	@WithDebug
    public Stream<Product> findByNameText(String text, int numberOfResults){
        QueryParser parser = new QueryParser("name", analyzer);
        Query query;
        try {
            query = parser.parse(text);
        } catch (ParseException e2) {
            throw new ProductRepositoryRuntimeException(e2);

        }
        IndexSearcher indexSearcher;
        try {
            searcherManager.maybeRefresh();
            indexSearcher = searcherManager.acquire();
        } catch (IOException e1) {
            throw new ProductRepositoryRuntimeException(e1);

        }
        try {
            TopDocs docs = indexSearcher.search(query, numberOfResults);
            return Arrays.stream(docs.scoreDocs).map(scoreDoc -> findByDocId(scoreDoc.doc));
        } catch (IOException e) {
            throw new ProductRepositoryRuntimeException(e);
        }
        finally{
            try {
                searcherManager.release(indexSearcher);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
	@WithDebug
    public Optional<Product> findById(String id) {
        QueryParser parser = new QueryParser("id", analyzer);
        Query query;
        try {
            query = parser.parse(id);
        } catch (ParseException e) {
            throw new ProductRepositoryRuntimeException(e);
        }
        TopDocs docs;
        IndexSearcher indexSearcher;
        try {
            searcherManager.maybeRefresh();
            indexSearcher = searcherManager.acquire();
        } catch (IOException e1) {
            throw new ProductRepositoryRuntimeException(e1);
        }
        try {
            docs = indexSearcher.search(query, 1);
            return Arrays.stream(docs.scoreDocs).map(scoreDoc -> findByDocId(scoreDoc.doc)).findFirst();
        } catch (IOException e) {
            throw new ProductRepositoryRuntimeException(e);
        }
        finally{
            try {
                searcherManager.release(indexSearcher);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
    
}
