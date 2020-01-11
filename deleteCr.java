public void deleteCr ( final String studentId){

            final DatabaseReference mRoot, mDatabase;

            mRoot = FirebaseDatabase.getInstance().getReference().child("app");
            mDatabase = mRoot.child("cr");
            mRoot.child("users").child("students").child(studentId).child("tag").setValue("default").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    mDatabase.child(studentId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(getApplicationContext(), "CR deleted successfully", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
