package es.urjc.code.dad.mail.batch;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import es.urjc.code.dad.mail.batch.model.Student;

public class StudentItemProcessor implements ItemProcessor<Student, MimeMessage> {

	private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

	@Autowired
	private JavaMailSender mailSender;
	private String sender;
	private String attachment;
	
	public StudentItemProcessor(String sender, String attachment) {
		this.sender = sender;
		this.attachment = attachment;
	}

	@Override
	public MimeMessage process(Student student) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setSubject("Your code");
		helper.setFrom(sender);
		helper.setTo(student.getEmail());
		helper.setText("This is your code: " + student.getCode() + ".\nFind instructions attached to this email.");
		
		log.info("Preparing message for: " + student.getEmail());
		
		FileSystemResource file = new FileSystemResource(attachment);
		helper.addAttachment(file.getFilename(), file);
		
		return message;
	}

	
}
