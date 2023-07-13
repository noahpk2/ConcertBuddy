<h1>ConcertBuddy (working name)</h1>

<h2>What is it?</h2>
<p>ConcertBuddy is an application that allows users to find concerts or other events in their area and connect with other users who are attending the same concert. Users can also create their own events and invite other users to join them.</p>



<h2>Major Components</h2>
<ul>
    <li>Frontend: Android Mobile Application</li>
    <li>Backend: Custom server written in Rust to handle users, events, and web scraping</li>
    <li>(Database): MongoDB for Users and Events, Neo4j to represent user friendships/groups in a graph style structure,</li>
</ul>


<h2>Frontend Components</h2>
<ul>
    <li>Calendar implemented with Recyclerview that displays events for the coming year</li>
    <li>Event view page that displays information about the event and allows users to join the event</li>
    <li>Event creation page that allows users to create their own events and invite other users</li>
    <li>Friend list page that displays a list of the user's friends and allows them to add new friends(not implemented yet)</li>
    <li>Friend view page that displays information about the friend and allows the user to remove the friend(not implemented yet)</li>
    <li>Profile page that displays information about the user and allows them to edit their information</li>
    <li>Search page that allows users to search for events and other users(not implemented yet)</li>
    <li>Settings page that allows users to change their settings(not implemented yet)</li>
    <li>Sign in page that allows users to sign in to their account</li>
    <li>Sign up page that allows users to create a new account</li>
</ul>

<h2>Backend Components</h2>
<ul>
    <li>Custom stateless server written in Rust using the tokio web framework(hosted locally for now, but designed to be easily cloud hosted and scaled - runs in a docker container)</li>
    <li>Server handles user authentication,user logins, event creation, storage, and retrieval, user and event search, and web scraping(partially implemented)</li>
    <li>Server uses MongoDB to store user and event information</li>
    <li>Server uses Neo4j to store user friendships and groups(this will allow for things like friend suggestions,and social network mapping)</li>
</ul>

<h2>How to run</h2>
<p> You can currently run the app (locally) via emulator or on an android device through Android Studio, albeit with limited functionality. If you want to connect to our server, youll need to request a private key to connect(not implemented yet). If you want to run your own server, 
youll need to download rust, docker, and images of mongoDB and neo4j and then run the server found in this github repo:(not implemented yet)</p>



<h2>Current Status</h2>
<ul>
    <li>Frontend: 70% complete</li>
    <li>Backend: 50% complete</li>
</ul>


<h2>Future Plans</h2>
<ul>
    <li>Since we're not far off from something that could actually be useful and fun for us and a lot of our friends, we'd like to actually publish this app. </li>
    <li>Given that many of us have iPhones, we're looking into porting the app to IOS if it's not too glitchy. The other option is a full rebuild.</li>
    <li>It should also be relatively simple to convert this into a web application so people can access it via desktop (or mobile site)</li>
</ul>
