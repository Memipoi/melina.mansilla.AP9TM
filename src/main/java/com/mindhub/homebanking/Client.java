package com.mindhub.homebanking;

import javax.persistence.Entity;

@Entity
public class Client {
        private String firstName;
        private String lastName;
        private String email;
        public Client() { }

        public Client(String first, String last) {
            firstName = first;
            lastName = last;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String toString() {
            return firstName + " " + lastName;
        }
}
