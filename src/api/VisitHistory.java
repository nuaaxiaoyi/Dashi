package api;

public class VisitHistory {
	private static final DBConnection connection = new MySQLDBConnection();
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			try {
				JSONObject input = RpcParser.parseInput(request);
				if (input.has("user_id") && input.has("visited")) {
					String userId = (String) input.get("user_id");
					JSONArray array = (JSONArray) input.get("visited");
					List<String> visitedRestaurants = new ArrayList<>();
					for (int i = 0; i < array.length(); i++) {
						String businessId = (String) array.get(i);
						visitedRestaurants.add(businessId);
					}
					connection.setVisitedRestaurants(userId, visitedRestaurants);
					RpcParser.writeOutput(response, new JSONObject().put("status", "OK"));
				} else {
					RpcParser.writeOutput(response, new JSONObject().put("status", "InvalidParameter"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
}
