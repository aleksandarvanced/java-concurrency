https://artillery.io/

Install NodeJS   
Install artillery   
`npm install -g artillery `

There is an option to execute load tests quickly  
`artillery quick --count 5 -n 5 http://localhost:8080/api/v1/idgeneration/id?domain=table1`  
count 5 means that there will be 5 users sending 5 requests each  
 
 
 Another approach which is more configurable is to run an actual script  
`artillery run idgen.yml`