    /*
    Created by Ashok for Grievance system on 11/10/2019
    */


    /*
    The below function is implemented inside a manual action button , which will be inside authority app to
    delete all CR students after a semester or any duration.
    The task is deleted into two parts i.e to delete all student tag from database and
    then delete the CR root node.
     */
    public void deleteCR() {

        final DatabaseReference mRootDatabase, mDatabase, mCrReference;
        mRootDatabase = FirebaseDatabase.getInstance().getReference().child("app");
        mDatabase = mRootDatabase.child("cr");
        mCrReference = mRootDatabase.child("users").child("students");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot crdata : dataSnapshot.getChildren()) {
                    String uid = crdata.getKey();
                    if (uid != null) {
                        mCrReference.child(uid).child("tag").setValue("default").addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("Cr_removal_Error", e.getMessage());
                            }
                        });
                    }
                }
                // The code to delete main CR node
                mDatabase.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar snackbar = Snackbar.make(rootlayout, "All CR are removed", Snackbar.LENGTH_LONG); // rootlayout need to be provided
                            snackbar.show();
                        } else
                            Log.v("CR_Node_Error", task.getException().getMessage());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("Error_in_students_node", databaseError.getMessage());
            }
        });

    }