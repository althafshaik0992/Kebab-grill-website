package Model;

import com.example.SpringRestaurantswebsite.Model.CustomerUser;
import com.example.SpringRestaurantswebsite.Model.Role;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CustomerUserTest {


    @Test
    public void testNameField() {
        CustomerUser cu = new CustomerUser();

        int id= 1;
        String name = "test";
        String password = "test";
        String email = "test@test.com";
        String phone = "123456789";
        String address = "test";
       // Set<Role> roles = new HashSet<>();
       // roles.add(Role.ADMIN);






        cu.setName(name);
        cu.setPassword(password);
        cu.setEmail(email);
        cu.setPhoneNumber(phone);
        cu.setAddress(address);
        cu.setId(id);




        assertEquals(name, cu.getName());
        assertEquals(password, cu.getPassword());
        assertEquals(email, cu.getEmail());
        assertEquals(phone, cu.getPhoneNumber());
        assertEquals(address, cu.getAddress());
        assertEquals(id, cu.getId());
      //  assertEquals(roles, cu.getRole());


    }

    @Test
    public void testConstructor_validInputs() {
        // Prepare valid inputs
        String email = "test@example.com";
        String password = "password";
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

        // Create instance and verify
        CustomerUser user = new CustomerUser(email, password, roles);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(roles, user.getRole());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_nullEmail() {
        String password = "password";
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

        new CustomerUser(null, password, roles);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_emptyEmail() {
        String email = "";
        String password = "password";
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

        new CustomerUser(email, password, roles);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_nullPassword() {
        String email = "test@example.com";
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

        new CustomerUser(email, null, roles);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_emptyPassword() {
        String email = "test@example.com";
        String password = "";
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);

        new CustomerUser(email, password, roles);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_nullRoles() {
        String email = "test@example.com";
        String password = "password";

        new CustomerUser(email, password, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_emptyRoles() {
        String email = "test@example.com";
        String password = "password";
        Set<Role> roles = new HashSet<>();

        new CustomerUser(email, password, roles);
    }
}
