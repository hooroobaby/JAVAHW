//20200113
// Person class that represents an entry in an address book.
//Create and manipulate each entry in Addresses
public class Person
{
   private int MemberID;
   private String name;
   private String type;
   private String phone;

   // constructor
   public Person()
   {
   } 

   // constructor
   public Person(int MemberID, String name, String type, String phone)
   {
      setMemberID(MemberID);
      setName(name);
      setType(type);
      setPhone(phone);
   } 

   // sets the addressID
   public void setMemberID(int MemberID)
   {
      this.MemberID = MemberID;
   }

   // returns the addressID 
   public int getMemberID()
   {
      return MemberID;
   }
   
   // sets the firstName
   public void setName(String name)
   {
      this.name = name;
   } 

   // returns the first name 
   public String getName()
   {
      return name;
   } 
   
   // sets the email address
   public void setType(String type)
   {
      this.type = type;
   }

   // returns the email address
   public String getType()
   {
      return type;
   } 
   
   // sets the phone number
   public void setPhone(String phone)
   {
      this.phone = phone;
   }

   // returns the phone number
   public String getPhone()
   {
      return phone;
   } 
} // end class Person


/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/

 