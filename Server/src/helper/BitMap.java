package helper;

// Routines to manage a bitmap -- an array of bits each of which
// can be either on or off.  Represented as an array of integers.
//
// Copyright (c) 1992-1993 The Regents of the University of California.
// All rights reserved.  See copyright.h for copyright notice and limitation 
// of liability and disclaimer of warranty provisions.

public class BitMap {
	
	private static final int BitsInWord = 32;
	
	private final int numBits;			// number of bits in the bitmap
	
	private final int numWords;			// number of words of bitmap storage
									// (rounded up if numBits is not a
									//  multiple of the number of bits in
									//  a word)
	
	private final int[] map;				// bit storage
	
	//----------------------------------------------------------------------
	// BitMap::BitMap
	//	 	Initialize a bitmap with "nitems" bits, so that every bit is clear.
	//		it can be added somewhere on a list.
	//
	//		"nitems" is the number of bits in the bitmap.
	//----------------------------------------------------------------------

	public BitMap(int nitems) { 
	    numBits = nitems;
	    numWords = (int) Math.ceil((numBits * 1.0) / BitsInWord);
	    map = new int[numWords];
        
	    clearAll();
	}

	//----------------------------------------------------------------------
	// BitMap::Set
	//	 	Set the "nth" bit in a bitmap.
	//
	//		"which" is the number of the bit to be set.
	//----------------------------------------------------------------------

	public void mark(int which) {
        synchronized(map) {
            if (which >= 0 && which < numBits) {
                map[(which / BitsInWord)] |= 1 << (which % BitsInWord);
            }
        }
	}
	    
	//----------------------------------------------------------------------
	// BitMap::Clear
	//	 	Clear the "nth" bit in a bitmap.
	//
	//		"which" is the number of the bit to be cleared.
	//----------------------------------------------------------------------

	public void clear(int which) {
        synchronized(map) {
            if (which >= 0 && which < numBits) {
                map[which / BitsInWord] &= ~(1 << (which % BitsInWord));
            }
        }
	}
	
	public void clearAll() {
        for (int i = 0; i < numBits; i++) {
            clear(i);
        }
	}

	//----------------------------------------------------------------------
	// BitMap::Test
	//	 	Return TRUE if the "nth" bit is set.
	//
	//		"which" is the number of the bit to be tested.
	//----------------------------------------------------------------------

	public boolean test(int which) {
	    if (which >= 0 && which < numBits) {
	    	if ((map[which / BitsInWord] & (1 << (which % BitsInWord))) != 0) {
	    		return true;
	    	}
	    }
	    
		return false;
	}

	//----------------------------------------------------------------------
	// BitMap::Find
	//	 	Return the number of the first bit which is clear.
	//		As a side effect, set the bit (mark it as in use).
	//		(In other words, find and allocate a bit.)
	//
	//		If no bits are clear, return -1.
	//----------------------------------------------------------------------

	public int find() {
	    for (int i = 0; i < numBits; i++) {
			if (!test(i)) {
			    mark(i);
			    
			    return i;
			}
	    }
	    
	    return -1;
	}

	//----------------------------------------------------------------------
	// BitMap::NumClear
//	 	Return the number of clear bits in the bitmap.
//		(In other words, how many bits are unallocated?)
	//----------------------------------------------------------------------

	public int numClear() {
	    int count = 0;

	    for (int i = 0; i < numBits; i++) {
	    	if (!test(i)) {
	    		count++;
	    	}
	    }
	    return count;
	}

}
