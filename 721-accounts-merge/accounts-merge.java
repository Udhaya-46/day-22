import java.util.*;

class Solution {

    class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }

    public List<List<String>> accountsMerge(List<List<String>> accounts) {

        int n = accounts.size();
        DSU dsu = new DSU(n);

        Map<String, Integer> emailToIndex = new HashMap<>();

        // Union accounts with common emails
        for (int i = 0; i < n; i++) {
            List<String> account = accounts.get(i);

            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);

                if (!emailToIndex.containsKey(email)) {
                    emailToIndex.put(email, i);
                } else {
                    dsu.union(i, emailToIndex.get(email));
                }
            }
        }

        // Group emails by parent account
        Map<Integer, TreeSet<String>> groups = new HashMap<>();

        for (String email : emailToIndex.keySet()) {
            int parent = dsu.find(emailToIndex.get(email));

            groups.putIfAbsent(parent, new TreeSet<>());
            groups.get(parent).add(email);
        }

        // Build result
        List<List<String>> result = new ArrayList<>();

        for (int parent : groups.keySet()) {
            List<String> merged = new ArrayList<>();

            merged.add(accounts.get(parent).get(0)); // Name
            merged.addAll(groups.get(parent));        // Sorted emails

            result.add(merged);
        }

        return result;
    }
}