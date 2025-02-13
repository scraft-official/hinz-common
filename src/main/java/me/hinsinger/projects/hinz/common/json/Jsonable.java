package me.hinsinger.projects.hinz.common.json;

public interface Jsonable {
	public default String toJson() {
		return JsonUtil.toJson(this);
	}
}
