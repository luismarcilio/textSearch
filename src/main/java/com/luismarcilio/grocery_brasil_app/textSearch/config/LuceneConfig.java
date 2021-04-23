package com.luismarcilio.grocery_brasil_app.textSearch.config;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import com.luismarcilio.grocery_brasil_app.textSearch.WithDebug;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import static org.apache.lucene.store.NIOFSDirectory.open;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuceneConfig {

    private final Directory directory;
    private final Analyzer analyzer;
    private final IndexWriterConfig indexWriterConfig;
    private final IndexWriter indexWriter;


    @Autowired
    public LuceneConfig(DirectoryPath directoryPath) throws IOException, URISyntaxException{
        this.directory = open(Paths.get(new URI(directoryPath.getPath())));
        this.analyzer = new BrazilianAnalyzer();
        this.indexWriterConfig = new IndexWriterConfig(analyzer);
        this.indexWriter = new IndexWriter(this.directory, this.indexWriterConfig);
    }

    @Bean
	@WithDebug
    public Directory getDirectory(){
        return this.directory;
    }
    @Bean 
	@WithDebug
    public IndexWriter getIndexWriter(){
        return this.indexWriter;
    }

    @Bean  
	@WithDebug
    public Analyzer getAnalyzer(){
        return this.analyzer;
    }

    @Bean 
	@WithDebug
    SearcherManager getSearcherManager() throws IOException{        
        return new SearcherManager(indexWriter,null);
    }

}
