package ayushproject.ayushecommerce.Batch;

import ayushproject.ayushecommerce.entities.Orders;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Orders> reader(){
        JdbcCursorItemReader<Orders> cursorItemReader = new JdbcCursorItemReader<>();
        cursorItemReader.setDataSource(dataSource);
        cursorItemReader.setSql("SELECT order_id, amount_paid, address_id  ,customer_id ,date_create ,date_updated, order_status FROM orders");
        cursorItemReader.setRowMapper(new OrderRowMapper());
        return cursorItemReader;
    }

    @Bean
    public OrderProcessor processor(){
        return new OrderProcessor();
    }

    @Bean
    public FlatFileItemWriter<Orders> writer(){
        FlatFileItemWriter<Orders> writer = new FlatFileItemWriter<Orders>();
        writer.setResource(new ClassPathResource("ayush.csv"));

        DelimitedLineAggregator<Orders> lineAggregator = new DelimitedLineAggregator<Orders>();
        lineAggregator.setDelimiter(",");

        BeanWrapperFieldExtractor<Orders> fieldExtractor = new BeanWrapperFieldExtractor<Orders>();
        fieldExtractor.setNames(new String[]{"order_id", "amount_paid", "address_id"  ,"customer_id" ,"date_create" ,"date_updated","order_status"});
        lineAggregator.setFieldExtractor(fieldExtractor);

        writer.setLineAggregator(lineAggregator);
        return writer;
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").<Orders,Orders>chunk(100).reader(reader()).processor(processor()).writer(writer()).build();
    }

    @Bean
    public Job exportOrderJob(){
        return jobBuilderFactory.get("exportOrderJob").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
    }
}
