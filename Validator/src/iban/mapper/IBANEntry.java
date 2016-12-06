package iban.mapper;

import java.util.Map.Entry;

public class IBANEntry<K, V> implements Entry<K, V> {
	private final K key;
	private V value;
	 
	IBANEntry(K key, V value){
		this.key = key;
		this.setValue(value);
	}
	
	@Override
	public K getKey(){
		return key;
	}
	
	@Override
	public V getValue() {
		return value;
	}
	
	@Override
	public V setValue(V value) {
		V oldValue = value;
		this.value = value;
		return oldValue;
	}
}
