package com.googleappengine.dao.impl;

import com.googleappengine.dao.WordEntityDao;
import com.googleappengine.model.WordEntity;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * DAO Implementation.
 * @author hnguyen.
 */
@Repository
@Transactional
public class WordEntityDaoImpl extends BaseDaoImpl<WordEntity, Long> implements WordEntityDao {
    private static final Logger LOG = LoggerFactory.getLogger(WordEntityDaoImpl.class.getCanonicalName());

    public String getClassName() {
        return WordEntity.class.getCanonicalName();
    }

    @SuppressWarnings("unchecked")
    /**
     * {@inheritDoc}
     */
    public WordEntity getWordByName(String word) {
        if (StringUtils.isEmpty(word)) {
            return null;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Getting word entity by name: %s", word));
        }
        String queryString = String.format("select from %s u where u.name=:word", getClassName());
        Query query = entityManager.createQuery(queryString);
        query.setParameter("word", word);

        List<WordEntity> result = query.getResultList();
        if (result != null && result.size() > 0) {
            return result.get(0);
        }
        return null;
    }
}
