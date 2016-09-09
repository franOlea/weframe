package com.weframe.model.user;

import com.weframe.model.user.fixture.RoleFixture;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RoleTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void createRole() {
        Role role = new Role(RoleFixture.ADMIN_ID, RoleFixture.ADMIN);

        Assert.assertEquals(role.getId(), RoleFixture.ADMIN_ID);
        Assert.assertEquals(role.getName(), RoleFixture.ADMIN);
    }

    @Test
    public void createRoleWithNegativeId() {
        exception.expect(IllegalArgumentException.class);

        new Role(-1, RoleFixture.ADMIN);
    }

    @Test
    public void createRoleWithNullName() {
        exception.expect(NullPointerException.class);

        new Role(RoleFixture.ADMIN_ID, null);
    }

    @Test
    public void createRoleWithEmptyName() {
        exception.expect(IllegalArgumentException.class);

        new Role(RoleFixture.ADMIN_ID, "");
    }

    @Test
    public void RoleEquals() {
        Assert.assertEquals(RoleFixture.admin(), new Role(RoleFixture.ADMIN_ID, RoleFixture.ADMIN));
    }

    @Test
    public void RoleToString() {
        Assert.assertEquals(RoleFixture.admin().toString(), new Role(RoleFixture.ADMIN_ID, RoleFixture.ADMIN).toString());
    }

    @Test
    public void RoleHashCode() {
        Assert.assertEquals(RoleFixture.admin().hashCode(), new Role(RoleFixture.ADMIN_ID, RoleFixture.ADMIN).hashCode());
    }
}
