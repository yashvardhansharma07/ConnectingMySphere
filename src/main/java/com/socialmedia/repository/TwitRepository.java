package com.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.socialmedia.model.Twit;
import com.socialmedia.model.User;

public interface TwitRepository extends JpaRepository<Twit, Long>  {
 
	List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();
	
	List<Twit> findByRetwitUserContainsOrUser_IdAndIsTwitTrueOrderByCreatedAtDesc(User user,Long userId);

	List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);
	
	@Query("Select t From Twit t JOIN t.likes I where I.user.id=:userId")
	List<Twit> findByLikesUser_id(Long userId);
	
	
}
