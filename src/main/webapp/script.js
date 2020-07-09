

fetch("/getRedditPosts").then((response) => response.json()).then((redditArray) => {
        //Print out the reddit post array to the console
        console.log(redditArray);
});