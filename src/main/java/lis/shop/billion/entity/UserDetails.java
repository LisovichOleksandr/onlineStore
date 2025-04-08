package lis.shop.billion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сутність, що представляє деталі користувача (UserDetails) в системі.
 * Має звʼязок один-до-одного з основною сутністю користувача (User).
 */
@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {

    /**
     * Унікальний ідентифікатор деталей користувача.
     * Є також зовнішнім ключем до таблиці users через анотацію @MapsId.
     */
    @Id
    private Long id;

    /**
     * Імʼя користувача.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Прізвище користувача.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Вік користувача.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Шлях до фотографії користувача (локальний шлях).
     */
    @Column(name = "photo_url")
    private String photoUrl;

    /**
     * Звʼязок з користувачем: один-до-одного.
     * Використовується @MapsId, що означає:
     * - первинний ключ цієї таблиці (id) також є зовнішнім ключем до таблиці users.
     * - звʼязок є об'єднаним ключем (shared primary key).
     * FetchType.LAZY означає, що дані користувача завантажуються лише при необхідності.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapsId
    private User user;
}
