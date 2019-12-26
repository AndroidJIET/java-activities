/*
  The function accept four parameter at the time of sign up by SignUpActivity and than generates a 
  a variable size id by trimming unnecessary 0's from input provided.
  Smallest length of id = 5
  Largest length of id = 7
*/

public String generateId(String mAdmissionYear, String mCollege, String mDepartment, String mRegDigit) {
        String mStudentId = "";
        boolean flag = false; /* Flag to check if the 0 occuring is before the non zero digit */
        char[] mRegDigitArray = mRegDigit.toCharArray();
        /*
        switch case for the generating id depending upon the given constraints for different colleges, as system applies for 
        more and more colleges the switch case can be updated accordingly
        */
        switch (mCollege) {

            case "jc":
                mStudentId = "0";
                break;
            case "ji":
                mStudentId = "1";
                break;
            default:
                break;
        }
        
        /*
        Switch case for department inside the college as more department gets added more case can be appended
        */
        
        switch (mDepartment) {

            case "cs":
                mStudentId = mStudentId + "0";
                break;
            case "ce":
                mStudentId = mStudentId + "1";
                break;
            case "ee":
                mStudentId = mStudentId + "2";
                break;
            case "ece":
                mStudentId = mStudentId + "3";
                break;
            case "me":
                mStudentId = mStudentId + "4";
                break;
            default:
                break;
        }
        
        mStudentId = mStudentId + mAdmissionYear; /* Addition of year, example 17,18 .. */

        /*
        Iteration to trim unneccasry input from the given 'n' length of string, but here the length is constant a 3 
        */
        
        for (int i = 0; i < 3; i++) {
            
            /*
            If condition to determine if the occuring 0 is unnecessary or required based upon the case that if it is occuring 
            before any non zero character.
            */
            
            if (!(mRegDigitArray[i] == '0'&&(!flag))) {
                flag = true;
                mStudentId = mStudentId + mRegDigitArray[i];
            }
        }
        return mStudentId;
    }
