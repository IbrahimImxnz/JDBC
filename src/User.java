public class User {
        private String userName;
        private String pwd;
        private String lastName;
        private String firstName;

        public User(String userName, String pwd, String lastName, String firstName) {
            this.userName = userName;
            this.pwd = pwd;
            this.lastName = lastName;
            this.firstName = firstName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
}


