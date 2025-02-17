package isc.etax.taxfiling.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.UUID;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Component
public class NanoUtil {
	
	private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	
    public String nanoIdGenerate(Integer size) {
        return NanoIdUtils.randomNanoId(new SecureRandom(), ALPHABET, size);
    }
}

