# Infra documentation


### Infrastructure diagram

![](https://i.postimg.cc/5t2gSXGD/disen-odered.jpg)

#### Internet Gateway: 
It is a component that allows bidirectional communication between the Amazon Web Services (AWS) Virtual Private Cloud (VPC) and the public Internet. It serves as the entry and exit point for network traffic between the VPC and the Internet. In simple terms, it is the link between your cloud infrastructure and the outside world.

#### EC2 Instance: 
EC2 (Elastic Compute Cloud) is an AWS service that provides scalable computing capabilities in the cloud. An EC2 instance is a virtual machine that we will launch and run in the AWS cloud. It is a virtual server where we will install and run digital booking application.

#### Public Subnet: 
A public subnet is a subdivision of the VPC that has a route to the Internet through the Internet Gateway. The EC2 instance connected to this subnet can have a public IP address assigned and is directly accessible from the Internet.

#### MySQL Database: 
MySQL is being used as the database engine. The database resides in a private subnet, which means it does not have a direct route to the Internet. This provides an additional layer of security by restricting direct access to the database from the public network.

#### Private Subnet: 
A private subnet is a subdivision of the VPC that does not have a direct route to the Internet. The MySQL database is located in this subnet to protect it from unauthorized access from the public Internet. Only resources within the VPC, such as the EC2 instance, can access the database in this subnet.

#### Bucket: 
It is a container for storing data in Amazon S3 (Simple Storage Service), a highly scalable cloud storage service. This will be our directory/folder in the cloud where you we will store and retrieve files and data such as images, videos, text files, etc.

### Documentacion

#### Terraform
Terraform files to deploy the infrastructure were added to this repository

#### CICD
Succesful deployment of the pipeline showed in the image

![](https://i.postimg.cc/501JqdP4/CICD-Digitalbooking.png)

#### AWS URL
Frontend Bucket S3:
http://c3-e10-digitalbooking-s3.s3-website.us-east-2.amazonaws.com

Backend EC2:
ec2-3-133-208-24.us-east-2.compute.amazonaws.com