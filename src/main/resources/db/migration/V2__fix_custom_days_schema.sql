-- Fix custom_days schema by creating separate table for ElementCollection

-- Drop the custom_days column from habits table
ALTER TABLE habits DROP COLUMN IF EXISTS custom_days;

-- Create habit_custom_days table for ElementCollection mapping
CREATE TABLE habit_custom_days (
    habit_id BIGINT NOT NULL,
    day_of_week VARCHAR(10) NOT NULL,
    FOREIGN KEY (habit_id) REFERENCES habits(id) ON DELETE CASCADE,
    PRIMARY KEY (habit_id, day_of_week)
);

-- Create index for better performance
CREATE INDEX idx_habit_custom_days_habit_id ON habit_custom_days(habit_id);