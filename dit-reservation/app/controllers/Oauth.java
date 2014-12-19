package controllers;

import com.dit.security.OAuth2Token;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.Logger;
import play.cache.Cache;
import play.mvc.Controller;
import play.mvc.Result;
import util.HttpConnector;

public class Oauth extends Controller {

    public static Result twitterCallBack(String oauth_token, String oauth_verifier) throws Exception {
        Logger.info("oauth_token ==> " + oauth_token);
        Logger.info("oauth_verifier ==> " + oauth_verifier);

        Config conf = ConfigFactory.load();
        String url = conf.getString("twitter.callback.url");
        Logger.info("URL ==> " + url);
        HttpConnector httpConnector = new HttpConnector();


        OAuth2Token token = httpConnector.doGet(url + "?oauth_token=" +  oauth_token + "&oauth_verifier=" + oauth_verifier, OAuth2Token.class);
        Cache.set("token", token, 3600 * 60 * 60 * 60);

        return redirect(routes.Application.index());
    }

}
