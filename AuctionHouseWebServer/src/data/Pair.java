package data;

import java.util.Map;

public class Pair<K, V> implements Map.Entry<K, V>{
	private K key;
	private V value;
	
	public Pair(K key, V value){
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		this.value = value;
		return value;
	}
	
	public K setKey(K key){
		this.key = key;
		return key;
	}
}
