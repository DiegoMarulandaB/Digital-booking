################################################
[Unit]
Description=DigitalBookingApplication
After=syslog.target

[Service]
User=myuser
Group=mygroup
ExecStart=/usr/bin/java -jar /path/to/DigitalBooking.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target

##########FIN DEL CODIGO#######################