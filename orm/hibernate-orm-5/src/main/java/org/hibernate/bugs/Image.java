package org.hibernate.bugs;


import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Vlad Mihalcea
 */
@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Image {

	@Id
	private Long id;

	@Lob
	@Basic( fetch = FetchType.LAZY )
	private byte[] content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
