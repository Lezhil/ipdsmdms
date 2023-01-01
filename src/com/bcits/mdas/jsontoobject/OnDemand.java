package com.bcits.mdas.jsontoobject;

public class OnDemand {
	
	    private Samples[] samples;

	    public Samples[] getSamples ()
	    {
	        return samples;
	    }

	    public void setSamples (Samples[] samples)
	    {
	        this.samples = samples;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [samples = "+samples+"]";
	    }
	}
	


