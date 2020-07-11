// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import com.squareup.okhttp.*;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import com.google.gson.*;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.stream.Collectors;


/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/getRedditPosts")
public class GetRedditPosts extends HttpServlet {
        
    @Override
    public void init() throws ServletException {

    }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    

    //Make the HTTP Request client, this will let us make HTTP POST Requests
    OkHttpClient client = new OkHttpClient();

    //Make the request to r/wholesomenews and store the list of posts in an array
    Request req = new Request.Builder()
    .url("https://www.reddit.com/r/wholesomenews/best/.json")
    .method("GET", null)
    .addHeader("Cookie", "loid=000000000077hktwpd.2.1594233772555.Z0FBQUFBQmZCaE9zVjE4dzliSkxXVzAwM2JLTnptY2d1cVJScU9VMC0ydWMxM2J"+
    "jSUIwSEdhczQxeWVlQmMwNkJ1cURTNW1VNWg3QkUwWkZxRXBxeWh1Ni1TalJwSVU0d3U1YkxVM3p2U0I2b2ptSmVhdVpNNjRTUlRHRkY5Nzhub2VKTUVKX2NMUnA;"+
    " edgebucket=z7KTn6rykpSJANkss3; csv=1; session_tracker=5mSIGfESTbeXYmCt8m.0.1594233793603.Z0FBQUFBQmZCaFBCWTUtUFREU2h4TS1oVkZ"+
    "mR0FvSHVFODFlVnlFdm56RHBRbm11OFFtNHJiMTJOS1JuT2xabmdzZUU2Yng5bmhrS0F4M0p0UjR3UEVXWURQOU4yR3Q3VUg2ckR0cDBCa1ltb3I1TjYxTzlQZEQxTE"+
    "h2MnpTZnYzWHJZT0pQVFBiU18")
    .build();

    Response res = client.newCall(req).execute();
    JsonObject convertedObject = new Gson().fromJson(res.body().string(), JsonObject.class);

    JsonArray wholesomeNews = convertedObject.getAsJsonObject("data").getAsJsonArray("children");

    //Make the request to r/news and store the list of posts in an array
    req = new Request.Builder()
    .url("https://www.reddit.com/r/news/best/.json")
    .method("GET", null)
    .addHeader("Cookie", "loid=000000000077hktwpd.2.1594233772555.Z0FBQUFBQmZCaE9zVjE4dzliSkxXVzAwM2JLTnptY2d1cVJScU9VMC0ydWMxM2J"+
    "jSUIwSEdhczQxeWVlQmMwNkJ1cURTNW1VNWg3QkUwWkZxRXBxeWh1Ni1TalJwSVU0d3U1YkxVM3p2U0I2b2ptSmVhdVpNNjRTUlRHRkY5Nzhub2VKTUVKX2NMUnA;"+
    " edgebucket=z7KTn6rykpSJANkss3; csv=1; session_tracker=5mSIGfESTbeXYmCt8m.0.1594233793603.Z0FBQUFBQmZCaFBCWTUtUFREU2h4TS1oVkZ"+
    "mR0FvSHVFODFlVnlFdm56RHBRbm11OFFtNHJiMTJOS1JuT2xabmdzZUU2Yng5bmhrS0F4M0p0UjR3UEVXWURQOU4yR3Q3VUg2ckR0cDBCa1ltb3I1TjYxTzlQZEQxTE"+
    "h2MnpTZnYzWHJZT0pQVFBiU18")
    .build();

    res = client.newCall(req).execute();
    convertedObject = new Gson().fromJson(res.body().string(), JsonObject.class);

    JsonArray regularNews =  convertedObject.getAsJsonObject("data").getAsJsonArray("children");


    //Combine the two arrays alternating good and regular news posts
    JsonArray combined = new JsonArray();

    for(int i = 0; i < Math.min(regularNews.size(),wholesomeNews.size()); i++){
        combined.add(wholesomeNews.get(i));
        combined.add(regularNews.get(i));
    }


    //Return the combined list as json to the calling client
    Gson gson = new Gson();
    String json = gson.toJson(combined);
    response.setContentType("text/html;");
    response.getWriter().println(json);

  }
}
