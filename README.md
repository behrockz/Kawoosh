# Kawoosh
Kawoosh is a DSE Diagnostic analyser developed specifically for the 40 point health check Datastax offers to anyone who has a cassandra cluster. 

Kawoosh doesn't have any overlap with Vector, but it does have some similarities with Nibbler and Monte Cristo. 

Most important differentiators of Kawoosh are:
* It stores the result of the diagnostics in a database, so the result of the previous health checks can be tracked and compared. 
* It uses Stargate underneath. 
* If a customer decides to use it on their own, they have to use Astra and Stargate! 
* The rules are flexible and easy to extend.
* Kawoosh reduces the time that SAs need to spend on this non-billable activity. 
* The resulting report can be compressed to a short 1-2 page, rather than the 40-50 page document the other tools generate. 

---
# To Run
You need to pass the following Input arguments if you need to upload a Diagnostic:

1. Upload
2. Absolute path to the diagnostic folder. 
3. Year (to identify the year the diagnostic tarball was collected)
4. Quarter (To identify the quarter the diagnostic tarball was collected)
5. Program (To identify which program this diagnostic tarball belongs to)
6. Group (To identify which group this diagnostic tarball belongs to)


You need to pass the following Input arguments if you want to retrieve the report you have already uploaded:

1. Report
2. Year (to identify the year the diagnostic tarball was collected.)
3. Quarter (To identify the quarter the diagnostic tarball was collected.)
4. Program (To identify which program this diagnostic tarball belongs to.)
5. Group (To identify which group this diagnostic tarball belongs to.)
6. ClusterName (Name of the cluster you want to see the report for)