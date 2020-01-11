/*
    position 		    -> the position of the recycler view adapter item on which the user has clicked.
    problemObjects  -> An ArrayList filled with the adapter item's data. 
    pId      		    -> the problem ID
    sId      		    -> the student ID
    likeCounter 	  -> it is the varible tracking the total likes for respective complaint(working globally).
    getLikedBy()    -> returns comma(,) seperated students ID who liked the post.
    getViewedBy()   -> returns comma(,) seperated students ID who viewed the post.
    getLikes() 		  -> return Total likes for a particular post
    getViews() 		  -> return Total views for a particular post
    */
    private void LikeIt(int position, String pId, String sId) {
        likeCounter = Integer.parseInt(problemObjects.get(position).getLikes());
        String likedBy = problemObjects.get(position).getLikedBy();
        //"-" indicates that the user has disliked the post liked before by him/her
        if (sId.contains("-")) {
            likeCounter--;
            //removing this sID from likedBy
            sId = "," + sId.replace("-", "");
            likedBy = likedBy.replace(sId, "");
        }
        //student liked the post
        else {
            likeCounter++;
            //adding the current student ID..>
            if (likedBy.equals("")) //for very first time
                likedBy += sId;
            else
                likedBy += "," + sId;
        }
        //changing values of problemObjects at particular index...>
        problemObjects.get(position).setpId(likedBy);
        problemObjects.get(position).setLikes("" + likeCounter);

        //storing new values to FirebaseDatabase;
        dbRef.child(pId).child("LikedBy").setValue(likedBy);
        dbRef.child(pId).child("likes").setValue("" + likeCounter);
    }

    private boolean hasViewed(int position, String pId, String sId) {
        if (problemObjects.get(position).getViewedBy().contains(sId)) {
            return false;
        }

        //adding the current student ID
        String viewedBy = problemObjects.get(position).getViewedBy();
        if (viewedBy.equals("")) {//for very first time an user viewed the post
            viewedBy += sId;
        } else
            viewedBy += "," + sId;

        //incrementing viewCounter...>
        int views = Integer.parseInt(problemObjects.get(position).getViews());
        views++;

        //changing values of problemObjects at particular index...>
        problemObjects.get(position).setViews("" + views);
        problemObjects.get(position).setpId(viewedBy);
        //storing new values to FirebaseDatabase;

        dbRef.child(pId).child("ViewedBy").setValue(viewedBy);
        dbRef.child(pId).child("views").setValue("" + views);

        return true;
    }
		/*
		**Implementation: hasViewed() ->
		if (hasViewed(position, problemObjects.get(position).getpId(), "99")) {
						viewText.setText(problemObjects.get(position).getViews());
						}
						Intent toLikedAndViewed = new Intent(PostsProblemsActivity.this, LikedAndUpvotedClass.class);
						startActivity(toLikedAndViewed);
				}
		**Implementation: LikeIt()    ->
		if (problemObjects.get(position).LikedBy.contains(sId)){
						liked.setImageResource(R.drawable.ic_thumb_up);
						LikeIt(position,problemObjects.get(position).getpId(),"-"+sId);
						likeText.setText(Integer.toString(likeCounter));
				}else{
						liked.setImageResource(R.drawable.ic_thumb_up_filled);
						LikeIt(position,problemObjects.get(position).getpId(),sId);
						likeText.setText(Integer.toString(likeCounter));
				}
		*/
		
