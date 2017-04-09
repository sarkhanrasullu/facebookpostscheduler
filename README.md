**_Facebook Post Scheduler_**

This application developed for post scheduling on facebook.<br>
we have 2 table in database:**page** and **posts**<br>
on posts table we have **file_type(int)** column.It means this post is picture(1),text(2) or video(3)
<br><br>
App is looking **page** table that is there any website url.If yes then parse these websites(parsing algorithm must be implement before
according to websites), fetch video title, description, urls from these websites and insert into posts table.
<br><br>
Then TimerTask is looking this post table if there is any posts in posts table select it and post on facebook.
<br><br>
Parsing the webpage is required for my app but you can reject that part and can insert posts to posts table manually
or by other way.App will select title,description,url,file_type from posts table and post it on facebook


If you have some questions feel free to contact me by email or facebook<br>

**email:** serxanresullu@gmail.com<br>
**facebook:** https://www.facebook.com/jsarkhanrasullu