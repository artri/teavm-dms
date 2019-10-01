package org.fastgrow.teadms.server.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	ID getId(T entity);

	T requireById(ID id);
	
	List<T> findByAttributeContainsText(String attributeName, String text);
	List<T> findByAttributeContainsText(String attributeName, String text, Pager pager);

	List<T> findByAttributeEqualsText(String attributeName, String text);
	List<T> findByAttributeEqualsText(String attributeName, String text, Pager pager);
	
	public static class Pager {
		private final int offset;
		private final int limit;
		
		public int getOffset() {
			return offset;
		}

		public int getLimit() {
			return limit;
		}

		private Pager(int offset, int limit) {
			this.offset = offset;
			this.limit = limit;
		}
		
		public static Pager of(int offset, int limit) {
			return new Pager(offset, limit);
		}
	}

	public static class DateFilter {
		private final Date startDate;
		private final Date endDate;

		public boolean hasStartDate() {
			return null != startDate;
		}

		public Date getStartDate() {
			return startDate;
		}

		public boolean hasEndDate() {
			return null != endDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		private DateFilter(Date startDate, Date endDate) {
			this.startDate = startDate;
			this.endDate = endDate;
		}

		public static DateFilter of(Date startDate, Date endDate) {
			return new DateFilter(startDate, endDate);
		}
	}
}
