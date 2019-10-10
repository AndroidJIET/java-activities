/* ShiftDatabase created by Abhishek Kachhwaha , on 10 October 2019 for Grievance System */


/*
     The function is used to shift database from one parent/child node to another node using datasnapshot
     the function accept two parameter i.e fromReference and ToReference and than move the respective nodes.

     @param fromReference : The reference is the intial reference from where we want to move/copy node
     @param toReference : The refernce is the final parent/child node to where we want to move/copy data

     The function makes a call to a function deleteNodes() which is called when we want to move the database,
     If we want the function to only copy the the data kindly skip the call to the deleteNodes() function.
     */
    private void shiftDatabase(final DatabaseReference fromReference, final DatabaseReference toReference) {
        fromReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    toReference.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                deleteNodes(fromReference); //function call to delete nodes once moved from fromReference , skip if you need to copy database
                            else {
                                Log.v("Error_in_SetValue", Objects.requireNonNull(task.getException()).getMessage());
                            }
                        }
                    });
                } else
                    Log.v("Error_in_Shift", "Datasnapshot does not contain any child node");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.v("Error_in_Connection", databaseError.getMessage());

            }
        });
    }

    /*
     The function is called once the nodes are copied from fromReference to toReference ,
     to remove redundancy this function is called i.e if you want to remove the original copy

     @param fromReference : The reference has the intial value of the database from where we want to shift database
     */
    private void deleteNodes(DatabaseReference fromReference) {

        fromReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Snackbar snackbar = Snackbar.make(rootlayout, "Changes made, Database moved", Snackbar.LENGTH_LONG); // rootlayout need to be provided
                    snackbar.show();
                }
            }
        });

    }
    
