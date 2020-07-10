# Preconditions
Create your personal solution
Focus on breadth rather than depth when cover expected output (Expected artifacts) points
Do your best to figure out industry best practices and utilize them properly
You have a time limit - 7 days
Tech challenge is your primary focus

# Problem statement
Implement a web crawler that traverses websites following predefined link depth (8 by default) and max visited pages limit (10000 by default). Web crawler starts from predefined URL (seed) and follows links found to dive deeper. The main purpose of this crawler to detect the presence of some terms on the page and collect statistics, e.g.

### Seed:
	https://en.wikipedia.org/wiki/Elon_Musk
### Terms:
Tesla, Musk, Gigafactory, Elon Mask  
Output:  
https://en.wikipedia.org/wiki/Elon_Musk 208 641 9 0  
acbd.com/page2.html  8 4 0 5 17  
qwerty.com/page1.html  3 2 0 2 7  
anothersite.com/page3.html  0 1 0 1 2  


Clarification:  
	https://en.wikipedia.org/wiki/Elon_Musk 208 641 9 0 858  
	Numbers are  
		Tesla - 208 hits  
		Musk - 641 hits  
		Gigafactory - 9 hits  
		Elon Mask - 0 hits  
		Total - 858 hits  

All stat data should be serialized into CSV file (no predefined sort)  
Top 10 pages by total hits must be printed to separate CSV file and console (sorted by total hits)  
## Expected artifacts
1. Source code provided through GitHub project
    - Focus on Java 11 LTS
    - Take into account project supportability
    - Focus on documentation
2. Env setup can be easily repeated
    - Add configuration and startup scripts
    - One button setup & configuration
    - Prepare sample data if necessary
3. Code follows industry best practices 
    - matches predefined code style - you can setup any
    - code coverage >40%
    - contains tests of several levels - unit, integration, etc.  
    
Provide a google drive link to your Demo session (up to 10m)
record a video proof that app works
cover both the happy path and failure/edge-case scenario
Take a code tour and clarify selected solutions
Prepare it in English

##### Settings `config.properties`
```properties
startURL = https://en.wikipedia.org/wiki/Elon_Musk
terms = Tesla, Musk, Gigafactory, Elon Mask
maxDepth = 8
maxURL = 20
```
##### Run
```
mvn compile
mvn exec:java
```
