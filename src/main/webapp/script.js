

fetch("/getRedditPosts").then((response) => response.json()).then((redditArray) => {

    var mainContainer = document.getElementsByTagName("main")[0];

    //Fills the top good and low bad section of the page with images and titles of the top articles
    document.getElementById('tgSnip').innerHTML = redditArray[0].data.title;
    document.getElementById('lbSnip').innerHTML = redditArray[1].data.title;

    setTopPic("topGoodFig", "tgImg", redditArray[0].data.thumbnail);
    setTopPic("lowBadFig", "lbImg", redditArray[1].data.thumbnail);
    
    for (var i = 2; i < redditArray.length; i++) {
        // Create each element to add onto the page
        var div = document.createElement("article");

        var figure = document.createElement("figure");
        var img = document.createElement("img");
        
        var header = document.createElement('h3'); // Title
        var infoLink = document.createElement("a");
        var info = document.createElement('p'); // URL for news source. 

        var footer = document.createElement('footer'); 
        var subredditLink = document.createElement("a");
        var subreddit = document.createElement('p'); // Post's orginal subreddit
        var date = document.createElement('p'); // Date of the post

        // Format into Json
        header.innerHTML = redditArray[i].data.title;
        infoLink.href = redditArray[i].data.url_overridden_by_dest;
        info.innerHTML = "Read More";

        infoLink.appendChild(info);

        subreddit.innerHTML = 'r/' + redditArray[i].data.subreddit;
        subredditLink.href = "http://reddit.com/"+redditArray[i].data.permalink;
        subredditLink.appendChild(subreddit);
        date.innerHTML = formatDate(new Date(1000 * redditArray[i].data.created_utc));

        setPicture(redditArray, i, figure, div, img);
        

        div.className = 'postBox';
        footer.className = 'artFoot';
        header.className = 'postTitle';
        subreddit.className = 'subreddit';
        date.className = 'date';
        info.className = 'postSnip';
        figure.className = 'artImg';

        figure.appendChild(img);

        div.appendChild(figure);
        div.appendChild(header);
        div.appendChild(infoLink);

        footer.appendChild(subredditLink);
        footer.appendChild(date);
        
        div.appendChild(footer) ;              
        
        mainContainer.appendChild(div);
    }
});


function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('/');
}

function setPicture(redditArray, i, figure, div, img){
    if (redditArray[i].data.thumbnail == ""){
        figure.style.display = "none";
        div.style.display = "block";
    } else {
        img.src = redditArray[i].data.thumbnail;
    }
}

function setTopPic(figureID, imageID, thumbnail){
    img = document.getElementById(imageID);
    figure = document.getElementById(figureID);
    if(thumbnail == ""){
        figure.style.display = "none";
    } else {
        img.src = thumbnail;
    }
}