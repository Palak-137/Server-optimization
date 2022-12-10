# Server-optimization

### Problem statement -
#### Web - Engage 
<p>
You are given 'N' number of servers from 1 to N that are being used to process client messages. Where 1 <= N < 20
<br/>
<br/>
Each server has infinite computational capacity but cannot handle more than one request at a time.
<br/>
<br/>
There are K number of clients from 1 to K. Where 1<= K <= 1000
<br/>
<br/>
There is an infinite input stream of json data in form of [ {"clientId" : 1, "data" : "Hello", "processingTime" : 10} , {"clientId" : 2, "data" : "world", "processingTime" : 5}..... ]
Where, 1 <= clientId <= K
<br/>
<br/>
processingTime =  Time taken by a server to process data in milliseconds.   

</p>

### solution 
Pull based algorithm with internal heavy and normal buffers
<br/>
<br/>
N: No of servers
<ul>
<li>We’ll keep avg processing time of last 2N messages as benchmark to define heavy and light tasks
<li>Scheduler will maintain N * 0.3 sized heavy queue and N * 0.7 sized normal queue
<li>Scheduler will initially poll for N Tasks and spread them in heavy and normal queue
<li>Whenever a server polls for a message
<ul>
<li>If normal queue is empty
<ul>
<li>If heavy queue is empty pull data from data source, arrange it and return from heavy one if present otherwise from normal one
<li>Else If heavy queue is not empty return from heavy queue
</ul>
<li>If normal queue is not empty
<ul>
<li>Return from normal queue
</ul>
</ul>

</ul>
</ul>

![image](https://user-images.githubusercontent.com/54790525/206855827-bababf13-4784-4f58-917a-9a560748e47d.png)


### How to run 🤠
<ul>
<li> Compile all java files
<li> Run the main file in the server folder by giving two arguments
<ul>
  <li> JSON file
  <li> number of server
</ul>

</ul>


## Output 

![image](https://user-images.githubusercontent.com/54790525/206856227-c62dbba9-63a6-4034-9a3a-0a4c52d9439a.png)

![image](https://user-images.githubusercontent.com/54790525/206856253-4939818a-6257-42f7-baf8-22a9b0ca8053.png)


