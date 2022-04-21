package org.mars;

import java.util.*;

public class java {
    /**
     * 剑指 Offer 09. 用两个栈实现队列
     */
    class CQueue {
        Deque<Integer> stack1;
        Deque<Integer> stack2;

        public CQueue() {
            stack1 = new LinkedList<Integer>();
            stack2 = new LinkedList<Integer>();
        }

        public void appendTail(int value) {
            stack1.push(value);
        }

        public int deleteHead() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            if (stack2.isEmpty()) {
                return -1;
            } else {
                int value = stack2.pop();
                return value;
            }
        }
    }

    class Node {
        int val;
        Node next, random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        if (head == null) return null;
        Node cur = head;
        // 1. 复制各节点，并构建拼接链表
        while (cur != null) {
            Node tmp = new Node(cur.val);
            tmp.next = cur.next;
            cur.next = tmp;
            cur = tmp.next;
        }
        // 2. 构建各新节点的 random 指向
        cur = head;
        while (cur != null) {
            if (cur.random != null)
                cur.next.random = cur.random.next;
            cur = cur.next.next;
        }
        // 3. 拆分两链表
        cur = head.next;
        Node pre = head, res = head.next;
        while (cur.next != null) {
            pre.next = pre.next.next;
            cur.next = cur.next.next;
            pre = pre.next;
            cur = cur.next;
        }
        pre.next = null; // 单独处理原链表尾节点
        return res;      // 返回新链表头节点
    }


    /**
     * 剑指 Offer 05. 替换空格
     *
     * @param s
     * @return
     */
    public String replaceSpace(String s) {

        StringBuilder string = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                string.append("%20");
            } else {
                string.append(c);
            }

        }
        return string.toString();
    }


    /**
     * 剑指 Offer 58 - II. 左旋转字符串
     *
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords(String s, int n) {
        StringBuilder res = new StringBuilder();
        for (int i = n; i < s.length(); i++)
            res.append(s.charAt(i));
        for (int i = 0; i < n; i++)
            res.append(s.charAt(i));
        return res.toString();
    }

    /**
     * 剑指 Offer 03. 数组中重复的数字
     */
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        int repeat = -1;
        for (int num : nums) {
            if (!set.add(num)) {
                repeat = num;
                break;
            }
        }
        return repeat;
    }

    /**
     * 剑指 Offer 53 - I. 在排序数组中查找数字 I
     */
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < target) {
                low = mid + 1;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                if (nums[low] == nums[high]) {
                    return high - low + 1;
                }
                if (nums[low] < target) {
                    low++;
                }
                if (nums[high] > target) {
                    high--;
                }
            }

        }
        return 0;
    }

    public int search1(int[] nums, int target) {
        return helper(nums, target) - helper(nums, target - 1);
    }

    int helper(int[] nums, int tar) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int m = (i + j) / 2;
            if (nums[m] <= tar) i = m + 1;
            else j = m - 1;
        }
        return i;
    }

    /**
     * 剑指 Offer 53 - II. 0～n-1中缺失的数字
     */
    public static int missingNumber(int[] nums) {
        int i = 0, j = nums.length;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] == mid) {
                i = mid;
            }
            if (nums[mid] > mid) {
                j = mid;
            } else if (nums[mid] < mid) {
                i = mid;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0,1,2,4};
        System.out.println(missingNumber(nums));
    }
}
