# Kawoosh

![Build](https://img.shields.io/badge/Build_with-Fun-orange.svg?style=for-the-badge)
![Java](https://img.shields.io/badge/-Java-orange.svg?style=for-the-badge&logo=java)
![Cassandra](https://img.shields.io/badge/-Cassandra-orange.svg?style=for-the-badge&logo=apache-cassandra)
![Gradle](https://img.shields.io/badge/-Gradle-orange.svg?style=for-the-badge&logo=gradle)

Kawoosh is a DSE Diagnostic analyser developed specifically for the 40 points health check Datastax offers to anyone who has a Apache Cassandra cluster. 

Kawoosh doesn't have any overlap with Vector, but it does have some similarities with Nibbler and Monte Cristo. 

Most important differentiators of Kawoosh are:
* It stores the result of the diagnostics in a database, so the result of the previous health checks can be tracked and compared. 
* It uses Stargate underneath. 
* If a customer decides to use it on their own, they have to use Astra and Stargate! 
* The rules are flexible and easy to extend.
* Kawoosh reduces the time that SAs need to spend on this non-billable activity. 
* The resulting report can be compressed to a short 1-2 pages, rather than the 40-50 pages document the other tools generate. 

_It is powered by Stargate and Astra. Kawoosh is a java project built by The Boring Team during Stargate Hackaton in September 2020._

---
# To Run
You need to pass the following Input arguments if you need to upload a Diagnostic:

1. Upload
2. Absolute path to the diagnostic folder. 
3. Year (to identify the year the diagnostic tarball was collected)
4. Quarter (to identify the quarter the diagnostic tarball was collected)
5. Program (to identify which program this diagnostic tarball belongs to)
6. Group (to identify which group this diagnostic tarball belongs to)


You need to pass the following Input arguments if you want to retrieve the report you have already uploaded:

1. Report
2. Year (to identify the year the diagnostic tarball was collected.)
3. Quarter (to identify the quarter the diagnostic tarball was collected.)
4. Program (to identify which program this diagnostic tarball belongs to.)
5. Group (to identify which group this diagnostic tarball belongs to.)
6. ClusterName (name of the cluster you want to see the report for)


You can use this as a sample diagnostic tarball: https://drive.google.com/file/d/1yS6EYMPDG2qmtpf2fXagTSuWQ9DT3W39/view?usp=sharing


