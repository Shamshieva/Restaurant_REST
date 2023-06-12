package manas.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
public class Cheque {
    @Id
    @SequenceGenerator(
            name = "cheques_id_gen",
            sequenceName = "cheques_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cheques_id_gen"
    )
    private Long id;
    private LocalDate createdAt;

    @ManyToOne(cascade =
            {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "cheques",
                    cascade =
                      {CascadeType.PERSIST,
                       CascadeType.MERGE,
                       CascadeType.REFRESH,
                       CascadeType.DETACH})
    private List<MenuItem> menuItems = new ArrayList<>();

}
