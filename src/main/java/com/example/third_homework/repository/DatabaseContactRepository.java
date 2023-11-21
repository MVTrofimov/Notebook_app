package com.example.third_homework.repository;

import com.example.third_homework.Contact;
import com.example.third_homework.repository.mapper.ContactRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactRepository implements ContactsRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        log.debug("Calling DatabaseContactRepository->findAll");

        String sql = "SELECT * FROM contacts";

        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling DatabaseContactRepository->findById with ID: {}", id);

        String sql = "SELECT * FROM contacts WHERE id = ?";
        Contact contact = DataAccessUtils.singleResult(jdbcTemplate.query(sql,
                new ArgumentPreparedStatementSetter(new Object[] {id}),
                new RowMapperResultSetExtractor<>(new ContactRowMapper(), 1)));

        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Calling DatabaseContactRepository->save with Contact: {}", contact);

        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contacts (id, firstName, lastName, email, tel_numb) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getTel_numb());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling DatabaseContactRepository->update with Contact: {}", contact);

        Contact existedContact = findById(contact.getId()).orElse(null);
        if (existedContact != null){
            String sql = "UPDATE contacts SET firstName = ?, lastName = ?, email = ?, tel_numb = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getTel_numb(), contact.getId());
            return contact;
        }

        log.warn("Contact with ID {} not found!", contact.getId());
        return null;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DatabaseContactRepository->deleteById with ID: {}", id);

        String sql = "DELETE FROM contacts WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
