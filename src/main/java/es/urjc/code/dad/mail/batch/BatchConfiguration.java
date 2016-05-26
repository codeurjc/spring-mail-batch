package es.urjc.code.dad.mail.batch;

import javax.mail.internet.MimeMessage;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import es.urjc.code.dad.mail.batch.model.Student;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Value("${spring.mail.username}")
	private String sender;

	@Value("${codeurjc.batch.data}")
	public String data;
	
	@Value("${codeurjc.batch.attachment}")
	private String attachment;
	
	@Value("${codeurjc.batch.notifications.email}")
	private String email;

	// tag::readerwriterprocessor[]
	@Bean
	public FlatFileItemReader<Student> reader() {
		FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(data));
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<Student>() {{
			setLineTokenizer(new DelimitedLineTokenizer() {{
				setNames(new String[] {"fullname", "code", "email"} );
			}});
			setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>(){{
				setTargetType(Student.class);
			}});
		}});
		return reader;
	}
	
	@Bean
	public StudentItemProcessor processor() {
		return new StudentItemProcessor(sender, attachment);
	}
	
	@Bean
	public MailBatchItemWriter writer() {
		MailBatchItemWriter writer = new MailBatchItemWriter();
		return writer;
	}
	// end::readerwriterprocessor[]
	
	// tag::listener[]

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener(email);
    }

    // end::listener[]
    
 // tag::jobstep[]
    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Student, MimeMessage> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]
}
