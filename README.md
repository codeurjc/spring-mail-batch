
# Mail batch application

A sample Spring Batch application to send emails to several recipients. It feeds email addresses from a csv file and sends an email to each recipient including an attachment. A sample csv file and attachment file are provided as samples. The code uses the [Spring Batch](http://projects.spring.io/spring-batch/ "Spring Batch")  project to configure a job for this task.

I've been using this application to send personal codes and specific instructions on how to register to some PaaS and IaaS providers to my students. The project is tailored to this specific use case, where instructions are sent as an attachment and the body of the email contains the personal code to redeem in order to get student access to some cloud provider.   

## How to run the application

Java 8 is required. To run the application:

    mvn clean package
    java -jar target/mail-batch-0.0.1-SNAPSHOT.jar \
      --spring.mail.host=<host> \
      --spring.mail.port=<port> \
      --spring.mail.username=<user> \
      --spring.mail.password=<pass> \
      --codeurjc.batch.data=data.csv \
      --codeurjc.batch.attachment=sample-attachment.txt
    
## Release changes

**0.2.0** 

* Add velocity templates for building email subject and body

**0.1.0** 

* Initial implementation 