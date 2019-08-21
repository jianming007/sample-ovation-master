package top.zhacker.ddd.identity.domain.group;

/***
 * 组成员类型-标准类型
 */
public enum GroupMemberType {

    Group {
        public boolean isGroup() {
            return true;
        }
    },

    User {
        public boolean isUser() {
            return true;
        }
    };

    public boolean isGroup() {
        return false;
    }

    public boolean isUser() {
        return false;
    }
}
