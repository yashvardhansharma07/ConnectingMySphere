package com.socialmedia.service;

import java.util.List;

import com.socialmedia.exception.UserException;
import com.socialmedia.model.Like;
import com.socialmedia.model.User;

public interface LikeService {

	public Like likeTwit(Long twitId, User user)throws UserException,TwitException;
    
	public List<Like>getAllLikes(Long twitId)throws TwitException;
}
