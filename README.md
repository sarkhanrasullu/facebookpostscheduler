# facebookpostscheduler

This application developed for post scheduling on facebook.
we have 2 table in database:page and posts
on posts table we have file_type(int) column it means this post is picture(1),text(2) or video(3)

App is looking 'page' table that is there any website url.If yes then parse these websites(parsing algorithm must be implement before
according to websites), fetch video title, description, urls from these websites and insert into posts table.

Then TimerTask is looking this post table if there is any posts in posts table select it and post on facebook.

Parsing the webpage is required for my app but you can reject that part and can insert posts to posts table manually
or by other way.App will select title,description,url,file_type from posts table and post it on facebook
