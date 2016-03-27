package es.urjc.code.dad.mail.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject("Job completion");
		message.setFrom("francisco.gortazar@urjc.es");
		message.setTo("patxi.gortazar@gmail.com");
		message.setText("Job completed with " + jobExecution.getExitStatus());

		mailSender.send(message);
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setSubject("Job started");
		message.setFrom("francisco.gortazar@urjc.es");
		message.setTo("patxi.gortazar@gmail.com");
		message.setText("Job started");

		mailSender.send(message);

	}

}
