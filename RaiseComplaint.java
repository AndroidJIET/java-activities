/*
    The function is used to raise a compliant if the user is CR , it takes data from user first and
    than takes input from parameter and than upload it to respective reference
    @param Title : Title is taken from user through UI
    @param Description : Description is taken from user
    @param Category : Category is taken from user through spinner
    @param Cell : Cell is taken from user from Spinner
     */


    public void raiseComplaint(String Title, String Description, final String Category, String Cell){
        final DatabaseReference mRootDatabase;
        final DatabaseReference mDatabase;
        final HashMap<String,String> mComplaintMap = new HashMap<>();
        mRootDatabase = FirebaseDatabase.getInstance().getReference().child("app");
        mDatabase = mRootDatabase.child("users").child("student");
        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid  = null;

        mComplaintMap.put("title",Title);
        mComplaintMap.put("description",Description);
        mComplaintMap.put("category",Category);
        mComplaintMap.put("cell",Cell);
        if (mCurrentUser != null) {
            uid = mCurrentUser.getUid();
        }
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               mComplaintMap.put("class",dataSnapshot.child("class").toString());
               mComplaintMap.put("section",dataSnapshot.child("section").toString());
               mComplaintMap.put("year",dataSnapshot.child("year").toString());
               mComplaintMap.put("department",dataSnapshot.child("department").toString());
               mComplaintmap.put("lastvisible","0");  //to see the active state of complaint

             
              mRootDatabase.child("complaints").child(Category).setValue(mComplaintMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Snackbar snackbar = new Snackbar(rootLayout,"Complaint Raised",Snackbar.LENGTH_LONG);
                           snackbar.show();
                       }
                       else {
                           Log.v("Error_In_Complaints",task.getException().getMessage());
                       }

                   }
               });

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
