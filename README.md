# SchoolTrack

A comprehensive academic tracking system for educational institutions, built with Java and JavaFX.

## Overview

SchoolTrack manages the complete academic lifecycle of students, including enrollment, class assignments, grade tracking, and payment management. The system maintains data integrity through synchronized operations and implements temporal access controls to protect historical records.

## Features

### Academic Management
- **Hierarchical Structure**: Organizes academic data in sections → classes → students
- **Subject Management**: Track subjects with coefficients and active/inactive status
- **Grade Tracking**: Automated grade generation and weighted average calculations
- **Report Cards**: Trimester-based bulletins with PDF export functionality

### Payment Management
- **Fee Categories**: Configurable payment rubriques per class
- **Payment Tracking**: Monitor tuition payments and installments
- **Cashier Dashboard**: Financial overview and payment processing

### Access Control
- **Role-based Access**: Different interfaces for administrators, secretaries, and cashiers
- **Temporal Restrictions**: Prevent modification of historical academic data
- **Academic Year Filtering**: All operations scoped to current academic year

## Architecture

### Data Models
- **Section**: Top-level division (Primary, Secondary, etc.)
- **Classe**: Grade/class level within a section
- **Matiere**: Subject with coefficient and status
- **Bulletin**: Trimester report card (3 per student per year)
- **Note**: Individual grade for a subject on a bulletin
- **Rubrique**: Payment category with amount and description

### Data Access Layer
- DAO pattern implementation for all entities
- Automatic note generation when subjects are added
- Synchronized bulletin creation on student enrollment
- Referential integrity through foreign key relationships

### User Interface
- JavaFX-based desktop application
- Role-specific dashboards and controllers
- Dynamic table updates with cascading choice boxes
- PDF generation for report cards

## Key Workflows

### Student Enrollment
1. Select section and class for current academic year
2. Add student to class
3. System automatically generates 3 bulletins (one per trimester)
4. Notes are created for all active subjects in each bulletin

### Grade Management
1. Open bulletin interface for specific student and trimester
2. Edit grades in table format
3. System calculates weighted average automatically
4. Export bulletin as PDF

### Subject Management
1. Add/edit subjects with coefficients
2. System updates all student bulletins in the class
3. Notes are generated/removed to maintain data consistency

## Technical Implementation

### Database Operations
- Connection pooling via DBManager
- Prepared statements for SQL injection prevention
- Transaction management for data consistency

### Business Logic
- Weighted average calculation: Σ(note × coefficient) / Σ(coefficient)
- Temporal validation using CURRENT_YEAR constant
- Automatic synchronization between related entities

### Error Handling
- Custom DAOException for database errors
- User-friendly error messages for validation failures
- Alert dialogs for operation feedback

## Development

### Project Structure
```
src/main/java/com/schooltrack/
├── controller/          # UI controllers
│   ├── admin/          # Administrator interfaces
│   ├── caissier/       # Cashier interfaces
│   └── secretaire/     # Secretary interfaces
├── models/             # Data models
│   └── datasource/     # DAO implementations
├── jdbc/               # Database connection management
├── pdf/                # PDF generation utilities
└── utils/              # Utility classes and constants
```

### Key Dependencies
- JavaFX for UI framework
- JDBC for database connectivity
- iText for PDF generation

## Security Considerations
- SQL injection prevention through prepared statements
- Role-based access control
- Temporal data protection
- Input validation on all forms
