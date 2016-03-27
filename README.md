
# Mail batch application

A sample Spring Batch application to send emails in bulk. It feeds email addresses from a csv file and sends an email to each recipient listed in the file with a file attached. A sample csv file and attachment file are provided as samples.    

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
      
