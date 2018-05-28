package com.papercutlabs.jobstar.network;

import com.papercutlabs.jobstar.constant.StaticConstant;

/**
 * Created by ritwik on 30-03-2018.
 */

public class Url {

    public static final String GOOGLE_NEWS_API = "https://newsapi.org/v2/top-headlines?sources=google-news&apiKey=" + StaticConstant.API_KEY_NEWS;

    public static final String GOOGLE_NEWS_QUERY = "https://newsapi.org/v2/everything?pageSize=10&q=" + "narendra%20modi" + "&apiKey=" + StaticConstant.API_KEY_NEWS;

    public static final String GOOGLE_NEWS_QUERY_JOB = "https://newsapi.org/v2/everything?pageSize=10&q=" + "interview%20job" + "&apiKey=" + StaticConstant.API_KEY_NEWS;

    public static final String YOUTUBE_QUERY = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&order=viewCount&q=" + "Buddha" + "&type=video&videoCaption=closedCaption&key=" + StaticConstant.API_KEY_YOUTUBE;

    public static final String BASE_URL = "http://papercutlabs.co/accounts/index.php/";

    public static final String REGISTRATION = BASE_URL + "/api/registration";

    public static final String FETCH_EMPLOYER = BASE_URL + "/api/getEmployer";

    public static final String ADD_EMPLOYER = BASE_URL + "/api/addEmployer";

    public static final String POST_JOB_URL = BASE_URL + "/api/postJob";

    public static final String UPLOAD_SEEKERS_RESUME_URL = BASE_URL + "/api/uploadResume";

    public static final String FETCH_JOBS = BASE_URL + "/api/getJobs";

    public static final String UPDATE_PROFILE_URL = BASE_URL + "/api/updateProfile";

}
