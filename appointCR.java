public void appointCr(final String studentId) {

        final DatabaseReference mRoot, mDatabase;

        mRoot = FirebaseDatabase.getInstance().getReference().child("app");
        mDatabase = mRoot.child("cr");
        mRoot.child("users").child("students").child(studentId).child("tag").setValue("CR").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mDatabase.child(studentId).child("extra").setValue("default").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Toast.makeText(getApplicationContext(), "CR successfully assigned", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
